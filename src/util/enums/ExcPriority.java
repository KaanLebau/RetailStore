package util.enums;
/**
 * Enum for exception priority. Priority is determined by 
 * the importance of the exception. An unknown exception 
 * and system exceptions have high priority.
 * Exceptions can be caused by the user have medium proirity.
 * Exceptions caused by business rules have low priority.
 * @author ozsan
 *
 */
public enum ExcPriority {
 LOW, MEDIUM, HIGH
}
