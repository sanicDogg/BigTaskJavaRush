package Horses;

public class Horse {
    public String name;
    public double speed;
    public double distance;

    public Horse(String name, double speed, double distance)
    {
        this.name = name;
        this.speed = speed;
        this.distance = distance;
    }

    public String getName()
    {
        return name;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public void move() {
        this.distance += this.speed * Math.random();
    }

    public void print() {
        for (int i = 1; i <= distance; i++) {
            System.out.print('.');
        }
        System.out.println(this.name);
    }
}
