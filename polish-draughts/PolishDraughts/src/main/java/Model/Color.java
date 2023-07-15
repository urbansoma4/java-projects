package Model;

public enum Color {
    WHITE('W'), BLACK('B');
    private final char letter;

    Color(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public Color changeColor() {
        if (this.getLetter() == Color.WHITE.letter){
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
