package perobobbot.tsgen;

import com.blueveery.springrest2ts.converters.ComplexTypeConverter;
import com.blueveery.springrest2ts.converters.JavaPackageToTsModuleConverter;
import com.blueveery.springrest2ts.converters.NullableTypesStrategy;
import com.blueveery.springrest2ts.converters.TypeMapper;
import com.blueveery.springrest2ts.implgens.EmptyImplementationGenerator;
import com.blueveery.springrest2ts.naming.ClassNameMapper;
import com.blueveery.springrest2ts.tsmodel.TSEnum;
import com.blueveery.springrest2ts.tsmodel.TSEnumConstant;
import com.blueveery.springrest2ts.tsmodel.TSModule;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.Scope;

/**
 * Created by tomek on 08.08.17.
 */
public class PerobobbotEnumConverter extends ComplexTypeConverter {
    private final boolean ordinalValue;



    public PerobobbotEnumConverter() {
        this(true);
    }

    public PerobobbotEnumConverter(ClassNameMapper classNameMapper) {
        this(classNameMapper, true);
    }

    public PerobobbotEnumConverter(boolean ordinalValue) {
        super(new EmptyImplementationGenerator());
        this.ordinalValue = ordinalValue;
    }

    public PerobobbotEnumConverter(ClassNameMapper classNameMapper, boolean ordinalValue) {
        super(new EmptyImplementationGenerator(), classNameMapper);
        this.ordinalValue = ordinalValue;
    }

    public boolean preConverted(JavaPackageToTsModuleConverter javaPackageToTsModuleConverter, Class javaClass) {
        if (TypeMapper.map(javaClass) == TypeMapper.tsAny) {
            TSModule tsModule = javaPackageToTsModuleConverter.getTsModule(javaClass);
            TSEnum tsEnum = new TSEnum(this.classNameMapper.mapJavaClassNameToTs(javaClass.getSimpleName()), tsModule);
            tsModule.addScopedElement(tsEnum);
            TypeMapper.registerTsType(javaClass, tsEnum);
            return true;
        } else {
            return false;
        }
    }

    public void convert(Class javaClass, NullableTypesStrategy nullableTypesStrategy) {
        TSEnum tsEnum = (TSEnum)TypeMapper.map(javaClass);
        Object[] enumConstants = javaClass.getEnumConstants();
        int nbEnums = enumConstants.length;

        for(int i = 0; i < nbEnums; ++i) {
            Enum enumConstant = (Enum)enumConstants[i];
            final var name = enumConstant.name();
            final var value = getTsValue(enumConstant);
            tsEnum.getTsEnumConstantList().add(new PeroEnumConstant(name,value, this.ordinalValue));
        }

        tsEnum.addAllAnnotations(javaClass.getAnnotations());
        this.conversionListener.tsScopedTypeCreated(javaClass, tsEnum);
    }

    private String getTsValue(Enum enumConstant) {
        if (enumConstant instanceof Scope s) {
            return s.getName();
        }
        if (enumConstant instanceof IdentifiedEnum i) {
            return i.getIdentification();
        }
        return enumConstant.name();
    }
}
