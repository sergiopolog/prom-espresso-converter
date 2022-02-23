package org.recreativospiscis.PromEspressoConverter.devices;

public class PROM82S129Specs implements PROMSpecs {

	public static final String PROM_TYPE = "82S129";

	// @formatter:off
    private static final String[] LABELS_IN =  {  "a6",  "a5",  "a4",  "a3",  "a0",  "a1",  "a2",    null,  null,  null,  null,  null,  null,  "a7" };
    private static final String[] LABELS_O  =  {  null,  null,  null,  null,  null,  null,  null,    "o4",  "o3",  "o2",  "o1",  null,  null,  null };
    private static final String[] LABELS_OE =  {  null,  null,  null,  null,  null,  null,  null,    null,  null,  null,  null,  "oe1", "oe2", null };
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
		return 0x1800;
	}

	@Override
	public int getMask_IN() {
		return 0x207F;
	}

	@Override
	public int getMask_O() {
		return 0x780;
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
		return 8;
	}

	@Override
	public int getPinCount_O() {
		return 4;
	}

	@Override
	public int getPinCount_OE() {
		return 2;
	}
}