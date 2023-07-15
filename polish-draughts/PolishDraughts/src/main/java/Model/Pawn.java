package Model;

public class Pawn {
    private final Color pawnColor;
    private Coordinates position;
    private Coordinates[] whereCanMove;
    private Coordinates[] whereCanCapture;

    public Pawn(Color color, Coordinates position) {
        this.pawnColor = color;
        this.position = position;
        this.whereCanMove = SWCM(position);
        this.whereCanCapture = SWCC(position);
    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public Coordinates[] getWhereCanMove() {
        return whereCanMove;
    }

    public void setWhereCanMove(Coordinates coordinates) {
        this.whereCanMove = SWCM(coordinates);
    }

    public Coordinates[] getWhereCanCapture() {
        return whereCanCapture;
    }

    public void setWhereCanCapture(Coordinates coordinates) {
        this.whereCanCapture = SWCC(coordinates);
    }

    public boolean isCanMove(Coordinates targetPosition) {
        boolean canMove = true;
        for (int i = 0; i < getWhereCanMove().length; i++) {
            if (targetPosition.getX() == getWhereCanMove()[i].getX() && targetPosition.getY() == getWhereCanMove()[i].getY()) {
                canMove = true;
                break;
            } else {
                canMove = false;
            }
        }
        return canMove;
    }


    public boolean isCanCapture(Coordinates targetPosition) {
        boolean canCapture = true;
        for (int i = 0; i < getWhereCanCapture().length; i++) {
            if (targetPosition.getX() == getWhereCanCapture()[i].getX() && targetPosition.getY() == getWhereCanCapture()[i].getY()) {
                canCapture = true;
                break;
            } else {
                canCapture = false;
            }
        }
        return canCapture;
    }

    public Coordinates[] SWCM(Coordinates position) {

        Coordinates[] possibilities = new Coordinates[]
                {new Coordinates(position.getX() - 1, position.getY() - 1),
                        new Coordinates(position.getX() - 1, position.getY() + 1),
                        new Coordinates(position.getX() + 1, position.getY() - 1),
                        new Coordinates(position.getX() + 1, position.getY() + 1)};
        return possibilities;
    }

    public Coordinates[] SWCC(Coordinates position) {
        Coordinates[] possibilities =
                {new Coordinates(position.getX() - 2, position.getY() - 2),
                        new Coordinates(position.getX() - 2, position.getY() + 2),
                        new Coordinates(position.getX() + 2, position.getY() - 2),
                        new Coordinates(position.getX() + 2, position.getY() + 2)};
        return possibilities;
    }
}
