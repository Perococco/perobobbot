package perobobbot.action;

import lombok.NonNull;

public interface NilActionBinder {

    @NonNull
    ActionBinding createBinding(@NonNull Object item);

    @NonNull
    default ActionBinding bind(@NonNull Object item) {
        final ActionBinding binding = createBinding(item);
        binding.bind();
        return binding;
    }
}
