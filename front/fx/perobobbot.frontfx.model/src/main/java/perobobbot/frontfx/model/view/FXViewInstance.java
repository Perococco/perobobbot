package perobobbot.frontfx.model.view;

import javafx.scene.Node;
import lombok.NonNull;
import perobobbot.fx.FXLoadingResult;
import perococco.perobobbot.frontfx.model.view.PeroFXViewInstance;

import java.util.Optional;

public interface FXViewInstance {

    @NonNull
    static FXViewInstance with(FXLoadingResult loadingResult) {
        return new PeroFXViewInstance(loadingResult);
    }

    /**
     * @return the node to display
     */
    @NonNull
    Optional<Node> node();

    void  showing();

    void  hiding();


    @NonNull
    FXViewInstance EMPTY = new FXViewInstance() {
        @Override
        public @NonNull Optional<Node> node() {
            return Optional.empty();
        }

        @Override
        public void showing() {}

        @Override
        public void hiding() {}
    };


}
