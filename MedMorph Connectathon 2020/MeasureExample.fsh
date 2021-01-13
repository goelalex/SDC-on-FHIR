Instance: MeasureAdrenalPrimaryTumor
InstanceOf: Measure
Description: "Measure of Adrenal Primary Tumor"
Usage: #example
* id = "MeasureAdrenalPrimaryTumor01"
* url = "urn:uuid:53fefa32-fcbb-4ff8-8a92-55ee1201289u18"
//SUSHI not liking identifier format for some reason
//* identifier = "MeasureAdrenalPrimaryTumor01"
* version = "0.1a"
* name = "MeasureAdrenalPrimaryTumor"
* title = "Measure Adrenal Primary Tumor"
//Not sure which status to use
* status = http://hl7.org/fhir/ValueSet/publication-status#draft "draft"
//* status = "draft"
* experimental = true
* publisher = "IHE SDC WG"
* description = "this is a test instance of a primary tumor measure from the adrenal CAP eCC"
//Grouper example based on CKey 
//code for measure to compare Adrenal Primary Tumor to site 
* group.stratifier.code = http://cap.org#2137.100004300 "Primary Tumor (pT)"
//not sure how to construct this FHIRPath expression
//component for by date, looking for all Primary Tumors in year 2020
* group.stratifier.component.criteria.language = http://hl7.org/fhir/ValueSet/expression-language#text/fhirpath "text/fhirpath"
* group.stratifier.component.criteria.expression = "Year: @2020"
//give option to use SNOMED code?