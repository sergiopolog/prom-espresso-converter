# prom-espresso-converter
PROM Espresso Converter


Converts PROM binary dump file into espresso minimizer tool file in order to get equivalent equations to be burned in a GAL.

Usage:
``java -jar prom-espresso-converter.jar <prom_type> <input_filename> [output_filename]``

Currently supported PROM types:
* 82S129
* 82S131
* 82S135

but more types could be freely suppoted by adding a new Device class into the proper package with PROM features.

Output file will contain a truth tables of all combinations of inputs and their related outputs, in espresso file format.