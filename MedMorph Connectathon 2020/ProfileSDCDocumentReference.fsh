Profile: IHESDCDocumentReference 
Parent: DocumentReference
Id: IHESDCDocumentReference
Title: "IHE SDC DocumentReference"
Description: "A profile for IHE SDC forms to be captured in FHIR"

//trying to create something similar to Search Questionnaire: http://build.fhir.org/ig/HL7/sdc/StructureDefinition-sdc-questionnaire-search.html
//Gaps: experimental (docStatus covers this), useContext, publisher, purpose, copyright, approvalDate, lastReviewDate, effectivePeriod
//Most of these CAP's are filled in the SDC form inside. How much of these gaps need to be filled? 
//Do we store the descriptions of elements in FSH?

* masterIdentifier 1..1 MS
* identifier MS //identifier is kind of a catch all for all identifying information currently for DocumentReference with IHE SDC
* docStatus 1..1
* type MS 
* category MS 
* date 1..1 MS
* description MS
* author MS 
* authenticator MS
* custodian 1..1 MS // How should we handle publishing organizations. Especially for blank vs. filled forms 
//should we attach filled forms to DiagnosticReport? - Thought that should be an "option"