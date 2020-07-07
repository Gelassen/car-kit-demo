package ru.autokit.android.providers

import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import ru.autokit.android.BaseTest

import org.mockito.MockitoAnnotations
import ru.autokit.android.model.CarSparesItem
import javax.inject.Inject

class ServiceProviderTest : BaseTest() {

    @Inject
    lateinit var subject: ServiceProvider

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        component.inject(this)
    }

    @Test
    fun testGetCachedSpares_dataIsEmpty_noDataset() {
        val observer = TestObserver<List<CarSparesItem>>()

        subject.getCachedSpares()
            .subscribe(observer)

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(ArrayList<CarSparesItem>())
    }

    @Test
    fun testGetCachedSpares_dataIsNotEmpty_DatasetIsReturned() {
        val model = ArrayList<CarSparesItem>()
        model.add(CarSparesItem("tool", "1", "2", "", "", ""))
        model.add(CarSparesItem("another tool", "1", "2", "", "", ""))
        subject.cacheSparesItems(model)
        val observer = TestObserver<List<CarSparesItem>>()

        subject.getCachedSpares()
            .subscribe(observer)

        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertResult(model)
    }
}