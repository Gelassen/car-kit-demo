package ru.autokit.android.providers

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.autokit.android.model.CarMileage
import ru.autokit.android.model.Vehicle

class ValidatorProviderTest {

    private lateinit var subject: ValidatorProvider

    @Before
    fun setUp() {
        subject = ValidatorProvider()
    }

    @Test
    fun validateOneValidVINTest() {
        val match = subject.validate("1J4GZ58S7VC697710")

        Assert.assertEquals(true, match)
    }

    @Test
    fun validateAnotherValidVINTest() {
        val match = subject.validate("3D7UT2CL2BG587350")

        Assert.assertEquals(true, match)
    }

    @Test
    fun validateNotValidVINWithSmallerLengthTest() {
        val match = subject.validate("1J4GZ58S7VC69771")

        Assert.assertEquals(false, match)
    }

    @Test
    fun validateNotValidVINWithBiggerLengthTest() {
        val match = subject.validate("1J4GZ58S7VC6977100")

        Assert.assertEquals(false, match)
    }

    @Test
    fun validateNotValidVINWithSpecialCharTest() {
        val match = subject.validate("1J4GZ58S7VC697710%")

        Assert.assertEquals(false, match)
    }

    @Test
    fun validateVehicle_notValidVin_isNotMatch() {
        val isValid = subject.validate(Vehicle())

        Assert.assertEquals(false, isValid)
    }

    @Test
    fun validateVehicle_validVehicle_isMatch() {
        val isValid = subject.validate(Vehicle("Nissan", "Juke", "2015"))

        Assert.assertEquals(true, isValid)
    }

    @Test
    fun validateMileage_validOne_isMatch() {
        val isMatch = subject.validate(CarMileage(1000000.toString()))

        Assert.assertEquals(true, isMatch)
    }

    @Test
    fun validateMileage_validSmallOne_isMatch() {
        val isMatch = subject.validate(CarMileage(10.toString()))

        Assert.assertEquals(true, isMatch)
    }

    @Test
    fun validateMileage_invalidTooBig_doesNotMatch() {
        val isMatch = subject.validate(CarMileage(10000000.toString()))

        Assert.assertEquals(false, isMatch)
    }

    @Test
    fun validateMileage_invalidNegative_doesNotMatch() {
        val isMatch = subject.validate(CarMileage("-10"))

        Assert.assertEquals(false, isMatch)
    }

    @Test
    fun validateMileage_invalidHasSpaces_doesNotMatch() {
        val isMatch = subject.validate(CarMileage("10 000"))

        Assert.assertEquals(false, isMatch)
    }

    @Test
    fun validateMileage_invalidHasSymbols_doesNotMatch() {
        val isMatch = subject.validate(CarMileage("10oo0"))

        Assert.assertEquals(false, isMatch)
    }

}