package ch.thewit.mrsynth.synth;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import ch.thewit.mrsynth.model.Envelope;
import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.Note;
import ch.thewit.mrsynth.model.NoteDefinition;
import ch.thewit.mrsynth.model.Waveform;

public class SynthesiserTest {

  @Test
  public void testOscillator() {
    NoteDefinition noteDefinition = new NoteDefinition(Note.A, 0, new Envelope(1000, 0, 1101, 0), Waveform.SINE, 0, 2,
        0.5);
    Synthesiser synth = new Synthesiser(44000);
    // List<IndexedSample> expected = new ArrayList<IndexedSample>();
    List<IndexedSample> actual = synth.synthesise(noteDefinition);

    assertEquals(101, actual.size());
    assertEquals(0.0, actual.get(0).getLSample(), 0.001);
    assertEquals(1.0, actual.get(25).getLSample(), 0.001);
    assertEquals(0.0, actual.get(50).getLSample(), 0.001);
    assertEquals(-1.0, actual.get(75).getLSample(), 0.001);
    assertEquals(0.0, actual.get(100).getLSample(), 0.001);

    assertEquals(1000L, actual.get(0).getIndex());
    assertEquals(1100L, actual.get(100).getIndex());
  }

  @Test
  public void testEnvelope() {
    NoteDefinition noteDefinition = new NoteDefinition(Note.A, 0, new Envelope(1000, 20, 2000, 100), Waveform.FULL_ONE,
        0, 2, 0.5);
    Synthesiser synth = new Synthesiser(10000);
    List<IndexedSample> actual = synth.synthesise(noteDefinition);

    assertEquals(1000, actual.size());
    assertEquals(0.05, actual.get(1).getLSample(), 0.001);
    assertEquals(0.1, actual.get(2).getLSample(), 0.001);
    assertEquals(1, actual.get(40).getLSample(), 0.001);
    assertEquals(0.01, actual.get(999).getLSample(), 0.001);
  }
}
