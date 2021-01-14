Instance: eCCDiagnosticReport
InstanceOf: http://hl7.org/fhir/us/core/StructureDefinition/us-core-diagnosticreport-note
Title: "eCC Diagnostic Report"
Description: "Example eCC Diagnostic report using US Core"
Usage: #example
LNC = http://loinc.org
* id = "eCCDiagnosticReport0"
* identifier.use = #usual 
* identifier.type = http://terminology.hl7.org/CodeSystem/v2-0203#MR "Medical Record Number"
* identifier.system = "http://hospital.example.org"
* identifier.value = "eCCDiagnosticReport0"
* status = #final
* category = LNC#LP7839-6 "Pathology"
* code = LNC#60568-3 "Pathology Synoptic report"
* subject = Reference(q201)
* effective.effectiveDateTime = "2021-01-14T10:00:00"
* performer = Reference(pathpract1)
* result = Reference(Adrenal.Bx.Res.129_3.002.011.RC1_sdcFDF1.FormInstanceVersionURI.2157)
* result = Reference(Adrenal.Bx.Res.129_3.002.011.RC1_sdcFDF.f96d3fea-62b3-47e9-8443-f199c2f946f0.ver1.49275.100004300)
* result = Reference(Adrenal.Bx.Res.129_3.002.011.RC1_sdcFDF1.FormInstanceVersionURI.43324)
* result = Reference(Adrenal.Bx.Res.129_3.002.011.RC1_sdcFDF1.FormInstanceVersionURI.56271)