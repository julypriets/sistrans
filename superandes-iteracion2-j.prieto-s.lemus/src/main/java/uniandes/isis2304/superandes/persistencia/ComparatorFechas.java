package uniandes.isis2304.superandes.persistencia;

import java.sql.Timestamp;
import java.util.Comparator;

public class ComparatorFechas implements Comparator<Timestamp> {

	@Override
	public int compare(Timestamp o1, Timestamp o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2);
	}
}