package com.example.ai.ann;

import static com.example.ai.TestUtils.assertExactlyEquals;

import org.junit.jupiter.api.Test;

public class ConnectionTest {
   @Test
   public void testAdjustWeight() {
      final Connection c = new Connection(7.5);
      c.adjustWeight(1.5);
      assertExactlyEquals(9.0, c.getWeight());
      c.adjustWeight(-9.5);
      assertExactlyEquals(-0.5, c.getWeight());
   }

   @Test
   public void testGetSignal() {
      final Connection c = new Connection(8.25);

      c.setInput(5.25);
      assertExactlyEquals(43.3125, c.getSignal());
      assertExactlyEquals(43.3125, c.getSignal()); // checks getSignal returns same result if input unchanged

      c.setInput(-12);
      assertExactlyEquals(-99.0, c.getSignal());
   }
}
