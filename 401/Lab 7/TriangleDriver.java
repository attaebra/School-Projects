public class TriangleDriver {
    public static void main(String[] args) {
        RightTriangle t = new RightTriangle(5, 10);
        System.out.println("The triangle's hypotenuse is "+t.getHypotenuse());
        System.out.println("The triangle's perimeter is "+t.getPerimeter());
    }
}