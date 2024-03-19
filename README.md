# spring-boot-webflux-part-event-api-example

Since Spring 6 and Spring boot 3, we can use the new PartEvent API for streaming multipart events to a Spring WebFlux
controller. The PartEvent API helps in handling the multipart data sequentially, in a streaming fashion.

1. Introduction
   When using part events, each part in a multipart HTTP message will produce at least one PartEvent instance containing
   both headers and a buffer with the contents of the part.

The multipart content can be sent through PartEvent objects:

FormPartEvent: a single object is produced for each form field, containing the field’s value.
FilePartEvent: one or more objects are produced containing the filename and content. If the file is large enough to be
split across multiple buffers, the first FilePartEvent will be followed by subsequent events.
We can use @RequestBody with Flux (or Flow in Kotlin) for accepting the multiparts on the server side.

2. Multipart File Upload Controller
   The following is an example of a file upload controller and its handler method accepting multipart events.

It uses PartEvent::isLast is true if there are additional events belonging to subsequent parts.
The Flux::switchOnFirst operator allows seeing whether we are handling a form field or file upload.
We can use the respective methods such as FormPartEvent.value(), FilePartEvent.filename() and PartEvent::content for
retriving the information from multipart upload parts.
Note that the body contents must be completely consumed, relayed, or released to avoid memory leaks.
