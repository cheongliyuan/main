package mams.logic;

import static mams.testutil.TypicalCommandHistory.INVALID_COMMAND_1;
import static mams.testutil.TypicalCommandHistory.INVALID_COMMAND_2;
import static mams.testutil.TypicalCommandHistory.INVALID_COMMAND_OUTPUT;
import static mams.testutil.TypicalCommandHistory.INVALID_IO_1;
import static mams.testutil.TypicalCommandHistory.INVALID_IO_2;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_1;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_2;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_OUTPUT_1;
import static mams.testutil.TypicalCommandHistory.VALID_COMMAND_OUTPUT_2;
import static mams.testutil.TypicalCommandHistory.VALID_IO_1;
import static mams.testutil.TypicalCommandHistory.VALID_IO_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    private CommandHistory commandHistory;

    @BeforeEach
    public void initialize() {
        this.commandHistory = new CommandHistory();
    }

    @Test
    public void addTest1() {
        this.commandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
        this.commandHistory.add(VALID_COMMAND_2, VALID_COMMAND_OUTPUT_2);
        assertEquals(Arrays.asList(VALID_IO_1, VALID_IO_2), this.commandHistory.getInputOutputHistory());
    }

    @Test
    public void addTest2() {
        this.commandHistory.add(INVALID_COMMAND_1, INVALID_COMMAND_OUTPUT);
        this.commandHistory.add(INVALID_COMMAND_2, INVALID_COMMAND_OUTPUT);
        assertEquals(Arrays.asList(INVALID_IO_1, INVALID_IO_2), this.commandHistory.getInputOutputHistory());
    }

    @Test
    public void hashCodeTest1() {
        this.commandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(VALID_COMMAND_2, VALID_COMMAND_OUTPUT_2);

        // same object -> same hashcode
        assertEquals(commandHistory.hashCode(), commandHistory.hashCode());

        // same internal values -> same hashcode
        assertEquals(commandHistory.hashCode(), sameCommandHistory.hashCode());

        // different internal values -> different hashcode
        assertNotEquals(commandHistory.hashCode(), differentCommandHistory.hashCode());

        // different object types -> different hashcode
        assertNotEquals(commandHistory.hashCode(), Objects.hash(0));
    }

    @Test
    public void hashCodeTest2() {
        this.commandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);

        CommandHistory appendedCommandHistory = new CommandHistory();
        appendedCommandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
        appendedCommandHistory.add(VALID_COMMAND_2, VALID_COMMAND_OUTPUT_2);

        CommandHistory anotherAppendedCommandHistory = new CommandHistory();
        anotherAppendedCommandHistory.add(VALID_COMMAND_1, VALID_COMMAND_OUTPUT_1);
        anotherAppendedCommandHistory.add(INVALID_COMMAND_2, VALID_COMMAND_OUTPUT_1);

        // internal value different -> different hashcode
        assertNotEquals(commandHistory.hashCode(), appendedCommandHistory.hashCode());
        assertNotEquals(commandHistory.hashCode(), anotherAppendedCommandHistory.hashCode());
    }

    @Test
    public void equals() {
        this.commandHistory.add(VALID_COMMAND_1, VALID_COMMAND_2);

        CommandHistory sameCommandHistory = new CommandHistory();
        sameCommandHistory.add(VALID_COMMAND_1, VALID_COMMAND_2);

        CommandHistory differentCommandHistory = new CommandHistory();
        differentCommandHistory.add(INVALID_COMMAND_1, INVALID_COMMAND_OUTPUT);

        // not same type, equals should return false
        assertFalse(commandHistory.equals(5));
        assertFalse(commandHistory.equals(new ArrayList<String>()));

        // compare valid history to null, should return false
        assertFalse(commandHistory.equals(null));

        // same object, returns true
        assertTrue(commandHistory.equals(commandHistory));

        // same internal values but different objects, returns true
        assertTrue(commandHistory.equals(sameCommandHistory));

        // different internal values, returns false
        assertFalse(commandHistory.equals(differentCommandHistory));
    }
}