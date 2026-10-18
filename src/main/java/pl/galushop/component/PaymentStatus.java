package pl.galushop.component;

/**
 * Enum representing the different statuses payment can have.
 */
public enum PaymentStatus {
    NEW,
    PENDING,
    CONFIRMED,
    REJECTED,
    ABANDONED,
    EXPIRED,
    ERROR
}
