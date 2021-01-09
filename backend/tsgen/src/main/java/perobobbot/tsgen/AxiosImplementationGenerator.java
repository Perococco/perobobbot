package perobobbot.tsgen;

import com.blueveery.springrest2ts.converters.TypeMapper;
import com.blueveery.springrest2ts.extensions.ModelSerializerExtension;
import com.blueveery.springrest2ts.implgens.BaseImplementationGenerator;
import com.blueveery.springrest2ts.tsmodel.*;
import com.blueveery.springrest2ts.tsmodel.generics.TSInterfaceReference;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.blueveery.springrest2ts.spring.RequestMappingUtility.getRequestMapping;

public class AxiosImplementationGenerator extends BaseImplementationGenerator {


    protected boolean useAsync;
    protected final String baseURLFieldName = "baseURL";
    protected final String[] implementationSpecificFieldsSet = {baseURLFieldName};
    protected final TSInterface baseUrlTsFieldType = new TSInterface("URL", TypeMapper.systemModule);
    protected final TSInterface promiseInterface = new TSInterface("Promise", TypeMapper.systemModule);
    protected final TSType axiosResponseInterface;
    protected final TSType axiosRequestConfigInterface;
    protected final TSType axiosInstance;

    public AxiosImplementationGenerator() {
        this(false);
    }

    public AxiosImplementationGenerator(boolean useAsync) {
        this.useAsync = useAsync;
        final var axiosModule = new TSModule("axios", null, false);
        this.axiosResponseInterface = new TSInterface("AxiosResponse", axiosModule);
        this.axiosRequestConfigInterface = new TSInterface("AxiosRequestConfig", axiosModule);
        this.axiosInstance = new TSElementAlias("axios", axiosModule, new TSInterface("AxiosStatic",axiosModule));
    }

    @Override
    protected String[] getImplementationSpecificFieldNames() {
        return implementationSpecificFieldsSet;
    }

    @Override
    public void write(BufferedWriter writer, TSMethod method) throws IOException {
        TSClass tsClass = (TSClass) method.getOwner();
        if (method.isConstructor()) {
            writeConstructorImplementation(writer, tsClass);
        } else {
            RequestMapping methodRequestMapping = getRequestMapping(method.getAnnotationList());
            RequestMapping classRequestMapping = getRequestMapping(method.getOwner().getAnnotationList());

            String tsPath = getEndpointPath(methodRequestMapping, classRequestMapping);
            String httpMethod = methodRequestMapping.method()[0].toString();

            String requestUrlVar = "url";
            String requestBodyVar = "body";
            String requestParamsVar = "url.searchParams";

            StringBuilder pathStringBuilder = new StringBuilder(tsPath);
            StringBuilder requestBodyBuilder = new StringBuilder();
            StringBuilder requestParamsBuilder = new StringBuilder();

            assignMethodParameters(method, requestParamsVar, pathStringBuilder, requestBodyBuilder, requestParamsBuilder);
            writeRequestUrl(writer, requestUrlVar, pathStringBuilder);

            boolean isRequestBodyDefined = !isStringBuilderEmpty(requestBodyBuilder);
            if (isRequestBodyDefined) {
                requestBodyVar = requestBodyBuilder.toString();
            }

            writer.write(requestParamsBuilder.toString());
            writer.newLine();

            final List<TSProperty> configProperties = new ArrayList<>();
            configProperties.add(new TSProperty("method","'"+httpMethod.toLowerCase()+"'"));
            configProperties.add(new TSProperty("url", requestUrlVar + ".toString()"));
            configProperties.addAll(composeRequestOptions(requestBodyVar, isRequestBodyDefined, httpMethod, methodRequestMapping.consumes()));

            writer.write("const config:AxiosRequestConfig = ");
            writer.write(configProperties.stream()
                                         .map(TSProperty::toString)
                                         .collect(Collectors.joining(",\n", "{\n", "};")));
            writer.newLine();

            writer.write(
                    "return axios(config)"
                            + getContentFromResponseFunction(method) + ";");
        }

    }

    protected void writeRequestUrl(
            BufferedWriter writer, String requestUrlVar, StringBuilder pathStringBuilder
    ) throws IOException {
        String tsPath = pathStringBuilder.toString();
        tsPath = tsPath.startsWith("/") ? tsPath : "/" + tsPath;
        writer.write("const " + requestUrlVar + " = " + " new URL('" + tsPath + ", this." + baseURLFieldName + ");");
        writer.newLine();
    }

    protected String getContentFromResponseFunction(TSMethod method) {
        TSType actualType = method.getType();

        String parseFunction = "";
        if (actualType == TypeMapper.tsNumber) {
            parseFunction = "res.text()).then(res => Number(res)";
        } else if (actualType == TypeMapper.tsBoolean) {
            parseFunction = "res.text()).then(res => (res === 'true')";
        } else if (actualType == TypeMapper.tsString) {
            parseFunction = "res.text()";
        } else if (actualType == TypeMapper.tsVoid) {
            return "";
        } else {
            ModelSerializerExtension modelSerializerExtension = this.modelSerializerExtension;
            parseFunction = modelSerializerExtension.generateDeserializationCode("res");
            return ".then(res => res.data.text()).then(res =>  " + parseFunction + ")";
        }
        return ".then(res =>  " + parseFunction + ")";
    }

    protected void initializeHttpParams(StringBuilder requestParamsBuilder, String requestParamsVar) {

    }

    @Override
    protected void addRequestParameter(StringBuilder requestParamsBuilder, String requestParamsVar, String queryParamVar) {
        requestParamsBuilder
                .append("\n")
                .append(requestParamsVar)
                .append(".append(").append(queryParamVar).append(".name")
                .append(",").append(queryParamVar).append(".value")
                .append(");");
    }

    protected List<TSProperty> composeRequestOptions(
            String requestBodyVar, boolean isRequestBodyDefined, String httpMethod, String[] consumesContentType
    ) {
        List<TSProperty> requestOptionsList = new ArrayList<>();
        if (("PUT".equals(httpMethod) || "POST".equals(httpMethod)) && isRequestBodyDefined) {
            requestOptionsList.add(getContentTypeHeader(consumesContentType));
            requestOptionsList.add(new TSProperty("data",modelSerializerExtension.generateSerializationCode(requestBodyVar)));
        }
        return requestOptionsList;
    }

    protected TSProperty getContentTypeHeader(String[] consumesContentType) {
        String contentType = getContentType(consumesContentType);
        return new TSProperty("headers", """
                {
                        'Content-Type': '%s'
                }""".formatted(contentType));
    }

    @Override
    public void changeMethodBeforeImplementationGeneration(TSMethod tsMethod) {
        if (isRestClass(tsMethod.getOwner()) && !tsMethod.isConstructor()) {
            tsMethod.setAsync(useAsync);
        }
    }

    @Override
    public TSType mapReturnType(TSMethod tsMethod, TSType tsType) {
        if (isRestClass(tsMethod.getOwner())) {
            if (tsType == TypeMapper.tsVoid) {
                return new TSInterfaceReference(promiseInterface, axiosResponseInterface);
            }
            return new TSInterfaceReference(promiseInterface, tsType);
        }
        return tsType;
    }

    @Override
    public List<TSParameter> getImplementationSpecificParameters(TSMethod method) {
        if (method.isConstructor()) {
            List<TSParameter> tsParameters = new ArrayList<>();
            TSParameter urlParameter = new TSParameter(baseURLFieldName, baseUrlTsFieldType, method, this, "new URL(window.document.URL)");
            tsParameters.add(urlParameter);
            return tsParameters;
        }
        return Collections.emptyList();
    }

    @Override
    public List<TSDecorator> getDecorators(TSMethod tsMethod) {
        return Collections.emptyList();
    }

    @Override
    public List<TSDecorator> getDecorators(TSClass tsClass) {
        return Collections.emptyList();
    }

    @Override
    public void addComplexTypeUsage(TSClass tsClass) {
        tsClass.addScopedTypeUsage(axiosResponseInterface);
        tsClass.addScopedTypeUsage(axiosRequestConfigInterface);
        tsClass.addScopedTypeUsage(axiosInstance);
    }

    @Override
    public void addImplementationSpecificFields(TSComplexElement tsComplexType) {
        TSClass tsClass = (TSClass) tsComplexType;
        if (tsClass.getExtendsClass() == null) {
            TSField baseUrlTsField = new TSField(baseURLFieldName, tsComplexType, baseUrlTsFieldType);
            tsClass.getTsFields().add(baseUrlTsField);
        }
    }
}
