package ch.thewit.mrsynth.synth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.thewit.mrsynth.model.IndexedSample;
import ch.thewit.mrsynth.model.NoteDefinition;

public class Synthesiser implements Serializable {

  private static final long serialVersionUID = 1465582523755234032L;
  private final int sampleRate;

  public Synthesiser(int sampleRate) {
    this.sampleRate = sampleRate;
  }

  public List<IndexedSample> synthesise(NoteDefinition note) {
    List<IndexedSample> result = new ArrayList<IndexedSample>((int) note.getLength());
    for (long i = note.getEnvelope().getStartSample(); i < note.getEnvelope().getEndSample(); i++) {
      double secondsPosition = (i - note.getEnvelope().getStartSample()) / (double) sampleRate;
      double wave = note.getWaveform().getValue(secondsPosition * note.getFrequency());
      double scaledWave = wave * note.getEnvelope().getVolume(i) * note.getVolume();
      double pan = note.getPan();
      result.add(new IndexedSample(i, scaledWave * (1 - pan), scaledWave * pan));
    }
    return result;
  }
}
