package org.example.conferenceservice.ClientRestOpenFeign;

import org.example.conferenceservice.model.Keynote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="keynote-service")
public interface KeynoteRestClient {
    @GetMapping("/api/keynotes/{id}")
    Keynote getKeynoteById(@PathVariable Long id);
}
