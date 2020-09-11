Instance: example0-SDC-Composition
InstanceOf: Composition
Description: "Example of Composition intended for a FHIR Document"
* id = "example0-SDC-Composition"
* identifier.use = #usual 
* identifier.type = http://terminology.hl7.org/CodeSystem/v2-0203#MR "Medical Record Number"
* identifier.system = "http://hospital.example.org"
* identifier.value = "SDC0Patient"
* status = "preliminary"
* type.CodeableConcept = LNC#27898-6 "Pathology studies"
* date = "2020-09-10T10:00:00"
* author = Reference(example-PathPract1)
* title = "Example of SDC Composition"
* attester.mode = official
* attester.party = Reference(example-SurgPract1)
* custodian = Reference(example-HHS)
* event.detail = Reference(procedurePath1)
* section.title = "Pathology Sample"
* section.code = 
* section.author = 
* section.focus = 
* section.entry = 

//Stopped working on this because not sure if it makes sense versus using Transaction Bundle