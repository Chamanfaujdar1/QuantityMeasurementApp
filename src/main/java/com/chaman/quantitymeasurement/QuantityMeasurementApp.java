package com.chaman.quantitymeasurement;

import java.util.Objects;

public class QuantityMeasurementApp {
	public static class Feet{
		private final double value;
		
		public Feet(double value) {
			this.value=value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) return true;
			if(obj==null || getClass()!=obj.getClass()) return false;
			Feet other=(Feet) obj;
			return Double.compare(this.value, other.value)==0;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(value);
		}
	}

	public static void main(String[] args) {
		Feet feet1=new Feet(88.0);
		Feet feet2=new Feet(88.0);
		if(feet1.equals(feet2)) {
			System.out.println("Both quantity are equal (True)");
		}else {
			System.out.println("Both quantity are not equal (False)");
		}
	}
}
