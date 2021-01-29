package perobobbot.rendering;

public class RenderingTools {

    public static double computeCellSize(double fullSize, int nbCells, double margin, double spacing) {
        return (fullSize - margin * 2 + spacing) / nbCells - spacing;
    }
}
