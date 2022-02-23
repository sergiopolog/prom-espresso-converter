package org.recreativospiscis.PromEspressoConverter.devices;

public class PROM82S135Specs implements PROMSpecs {

	public static final String PROM_TYPE = "82S135";

	// @formatter:off
    private static final String[] LABELS_IN =  {  "a0",  "a1",  "a2",  "a3",  "a4",  null,  null,  null,  null,    null,  null,  null,  null,  null,  null,  "a5",  "a6",  "a7" };
    private static final String[] LABELS_O  =  {  null,  null,  null,  null,  null,  "o1",  "o2",  "o3",  "o4",    "o5",  "o6",  "o7",  "o8",  null,  null,  null,  null,  null };
    private static final String[] LABELS_OE =  {  null,  null,  null,  null,  null,  null,  null,  null,  null,    null,  null,  null,  null,  "oe1", "oe2", null,  null,  null };
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
		return 0x6000;
	}

	@Override
	public int getMask_IN() {
		return 0x3801F;
	}

	@Override
	public int getMask_O() {
		return 0x1FE0;
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
		return 20;
	}

	@Override
	public int getPinCount_IN() {
		return 8;
	}

	@Override
	public int getPinCount_O() {
		return 8;
	}

	@Override
	public int getPinCount_OE() {
		return 2;
	}

}