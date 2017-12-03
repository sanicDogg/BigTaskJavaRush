package Space;

/**
 * Класс для объектов-ракета
 */
public class Rocket  extends BaseObject
{

    public Rocket(double x, double y)
    {
        super(x, y, 1);
    }

    /**
     * Метод рисует свой объект на "канвасе".
     */
    @Override
    public void draw(Canvas canvas)
    {
        canvas.setPoint(x,y,'|');
    }

    /**
     * Двигаем себя вверх на один ход.
     */
    @Override
    public void move()
    {
        y--;
    }
}
