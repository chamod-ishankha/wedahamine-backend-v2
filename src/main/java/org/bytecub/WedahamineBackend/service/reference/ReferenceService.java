package org.bytecub.WedahamineBackend.service.reference;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ReferenceService {
    private final CategoryService categoryService;
}
