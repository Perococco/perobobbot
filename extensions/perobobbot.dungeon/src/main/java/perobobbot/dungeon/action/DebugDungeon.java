package perobobbot.dungeon.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.dungeon.DungeonExtension;
import perobobbot.lang.ExecutionContext;
import perococco.jdgen.api.JDGenConfiguration;

@RequiredArgsConstructor
public class DebugDungeon implements CommandAction {

    private Thread thread = null;

    private final @NonNull DungeonExtension extension;

    @Override
    @Synchronized
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        final var size = parsing.findIntParameter("size").orElse(-1);

        if (size<=0) {
            return;
        }

        final var seed = parsing.findIntParameter("seed").orElse(4);
        this.thread = new Thread(new Runner(size, seed), "Dungeon Debug");
        this.thread.setDaemon(true);
        this.thread.start();

    }

    @RequiredArgsConstructor
    private class Runner implements Runnable {

        private final int size;
        private final int seed;

        @Override
        public void run() {
            do {
                try {
                    startDungeon();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Throwable e) {
                    e.printStackTrace();
                    //ignored
                }
            } while (!Thread.currentThread().isInterrupted());
            stopDungeon();
        }

        private void startDungeon() {
            final var minRoomSize = 2;
            final var maxRoomSize = 6;
            final var configuration = new JDGenConfiguration(seed, size, minRoomSize, maxRoomSize, 1.25);
            extension.start(configuration);
        }

        private void stopDungeon() {
            extension.stop();
        }
    }
}
