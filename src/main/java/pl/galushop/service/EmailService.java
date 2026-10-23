package pl.galushop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {


    private String generateActiveCode() {
        int activeCode = new Random().nextInt(10_000, 100_000);
        return String.valueOf(activeCode);
    }
}
