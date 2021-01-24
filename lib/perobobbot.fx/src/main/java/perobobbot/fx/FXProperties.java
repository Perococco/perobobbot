package perobobbot.fx;

import javafx.application.HostServices;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FXProperties {

    @Getter
    @NonNull
    private final Stage primaryStage;

    @Getter
    @NonNull
    private final HostServices hostServices;



}
