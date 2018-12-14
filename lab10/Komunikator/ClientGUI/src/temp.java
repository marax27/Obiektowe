import java.io.*;

import Shapes.*;

public class temp{
	public static void main(String args[]) throws IOException, FileNotFoundException, ClassNotFoundException{
		try{
		System.out.println("XXX");

		Rectangle r = new Rectangle(5, 20, new Pos2D(5, -1));

		FileOutputStream fos = new FileOutputStream("test.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(r);

		FileInputStream fis = new FileInputStream("test.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		Rectangle r1 = (Rectangle)ois.readObject();
		System.out.format("%f x %f (%d, %d)\n", r1.getA(), r1.getB(), r1.getPos().x, r1.getPos().y);
		}catch(NotSerializableException exc){
			System.err.println("NotSerializable: " + exc.getMessage());
		}
	}
}