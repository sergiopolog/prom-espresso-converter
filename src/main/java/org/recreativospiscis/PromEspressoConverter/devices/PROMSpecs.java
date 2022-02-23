package org.recreativospiscis.PromEspressoConverter.devices;

public interface PROMSpecs {

	public int getByteSize();

	public int getPinCount_Total();

	public int getPinCount_IN();

	public int getPinCount_O();

	public int getPinCount_OE();

	public int getMask_CE();

	public int getMask_IN();

	public int getMask_O();

	public String[] getLabels_IN();

	public String[] getLabels_O();

	public String[] getLabels_OE();
}