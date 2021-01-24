package perobobbot.fx.dialog;

import com.google.common.collect.ImmutableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Builder;
import lombok.NonNull;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.GraphicDecoration;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.CompoundValidationDecoration;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;
import perobobbot.i18n.Dictionary;
import perobobbot.validation.ValidationError;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Builder
public class ControlInfo {

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final Control control;

    @NonNull
    private final ValidationDecoration validationDecoration = new CompoundValidationDecoration(new GraphDeco(), new StyleClassValidationDecoration());

    public void updateDecoration(@NonNull ImmutableList<perobobbot.validation.ValidationError> errors) {
        final Optional<ValidationMessage> message = errors.stream()
                                                          .findFirst()
                                                          .map(ValidationError::getErrorType)
                                                          .map(t -> "error-type." + t)
                                                          .map(dictionary::value)
                                                          .map(m -> ValidationMessage.error(control, m));

        try {
            validationDecoration.removeDecorations(control);

            message.ifPresent(validationDecoration::applyValidationDecoration);
        } catch (Exception e) {
            throw e;
        }
    }

    private static class GraphDeco extends GraphicValidationDecoration {
        @Override
        protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
            final Node node=  createDecorationNode(message);
            final Image image = extractImage(node);
            final double xOffset;
            final double yOffset;
            if (image != null) {
                final double imageHeight = image.getHeight();
                final double imageWidth = image.getWidth();
                xOffset = -imageHeight/4;
                yOffset = +imageWidth/4;
            } else {
                xOffset = 0;
                yOffset = 0;
            }

            return Arrays.asList(new GraphicDecoration(node, Pos.TOP_RIGHT, xOffset,yOffset));
        }

        private Image extractImage(Node node) {
            if (node == null) {
                return null;
            }
            if (node instanceof ImageView) {
                return ((ImageView) node).getImage();
            }
            if (node instanceof Labeled) {
                return extractImage(((Labeled) node).getGraphic());
            }
            return null;
        }

    }

}
