package org.recreativospiscis.PromEspressoConverter.devices;

public class PROM82S123Specs implements PROMSpecs {

	public static final String PROM_TYPE = "82S123";

	// @formatter:off
    private static final String[] LABELS_IN =  {  null,  null,  null,  null,  null,  null,  null,    null,  "a0",  "a1",  "a2",  "a3",  "a4",  null  };
    private static final String[] LABELS_O  =  {  "o1",  "o2",  "o3",  "o4",  "o5",  "o6",  "o7",    "o8",  null,  null,  null,  null,  null,  null  };
    private static final String[] LABELS_OE =  {  null,  null,  null,  null,  null,  null,  null,    null,  null,  null,  null,  null,  null,  "oe1" };
	// @formatter:on

	@Override
	public int getByteSize() {
		return Double.valueOf(Math.pow(2, this.getPinCount_IN())).intValue();
	}

	@Override
	public String toString() {
		return "PROM" + PROM_TYPE;
	}

	@Override
	public int getMask_CE() {
		return 0x2000;
	}

	@Override
	public int getMask_IN() {
		return 0x1F00;
	}

	@Override
	public int getMask_O() {
		return 0xFF;
	}

	@Override
	public String[] getLabels_O() {
		return LABELS_O;
	}

	@Override
	public String[] getLabels_IN() {
		return LABELS_IN;
	}

	@Override
	public String[] getLabels_OE() {
		return LABELS_OE;
	}

	@Override
	public int getPinCount_Total() {
		return 16;
	}

	@Override
	public int getPinCount_IN() {
		return 5;
	}

	@Override
	public int getPinCount_O() {
		return 8;
	}

	@Override
	public int getPinCount_OE() {
		return 1;
	}

}