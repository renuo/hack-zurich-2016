package ch.renuo.hackzurich2016.qrcode;

import com.google.zxing.common.BitMatrix;

import junit.framework.Assert;

import org.junit.Test;

import java.util.UUID;

import ch.renuo.hackzurich2016.models.Household;
import ch.renuo.hackzurich2016.models.HouseholdImpl;

import static junit.framework.Assert.*;

public class QRcodeInterfaceTest {

    @Test
    public void testGenerate() throws Exception {
        Household household = new HouseholdImpl(UUID.randomUUID(), null);
        BitMatrix bitMatrix = QRcodeInterface.generateBitMatrix(household);
        assertNotNull(bitMatrix);
    }
}
