package perobobbot.rendering;

import lombok.Builder;
import lombok.NonNull;

import java.awt.*;

public interface BlockBuilder {

    @NonNull BlockBuilder setBackgroundColor(@NonNull Color color);

    @NonNull BlockBuilder setBackgroundMargin(@NonNull int margin);

    @NonNull BlockBuilder addString(@NonNull String text, @NonNull HAlignment alignment);

    @NonNull BlockBuilder addSeparator();

    @NonNull BlockBuilder setFontSize(float fontSize);

    @NonNull BlockBuilder setFont(@NonNull Font font);

    @NonNull BlockBuilder setColor(@NonNull Color color);

//    @NonNull BlockBuilder addBlock(@NonNull Block block, @NonNull HAlignment alignment);

    @NonNull Block build();
}
