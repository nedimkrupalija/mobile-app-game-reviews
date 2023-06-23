package ba.etf.rma23.projekat

/*
@RunWith(AndroidJUnit4::class)
class TestLayout {
    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                Is(n)
            )
        }
    }

    @get:Rule
    var homeRule:ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun elementsTest(){
        onView(withId(R.id.logo_image)).check(isCompletelyLeftOf(withId(R.id.home_button)))
        onView(withId(R.id.home_button)).check(isTopAlignedWith(withChild(withId(R.id.home_button))))
        onView(withId(R.id.details_button)).check(isTopAlignedWith(withId(R.id.home_button)))
        onView(withId(R.id.search_button)).check(isCompletelyBelow(withId(R.id.home_button)))
        onView(withId(R.id.search_button)).check(isTopAlignedWith(withId(R.id.search_query_edittext)))
    }

    @Test
    fun recyclerViewTest(){
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0)).check(matches(allOf(
            hasDescendant(withId(R.id.game_title_textview)),
            hasDescendant(withId(R.id.game_rating_textview)),
            hasDescendant(withId(R.id.release_date)),
            hasDescendant(withId(R.id.game_platform_textview)),
            hasDescendant(withId(R.id.game_rating_textview))
        )))

        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0)).check(
            matches(hasDescendant(withId(R.id.game_title_textview)).also { isTopAlignedWith(
                withChild(withId(R.id.game_title_textview))
            ) }))
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0)).check(
            matches(hasDescendant(withId(R.id.game_title_textview)).also { isCompletelyRightOf(
                withId(R.id.game_rating_textview)
            ) }))
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<ViewHolder>(0)).check(
            matches(hasDescendant(withId(R.id.game_rating_textview)).also { isCompletelyLeftOf(
                withId(R.id.game_platform_textview)
            ) }))
    }

    @Test
    fun recyclerViewTest2(){
        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollTo<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        )))
    }
    @Test
    fun recyclerViewTest3(){
        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))
        onView(withText(prvaIgra.description)).check(matches(isCompletelyDisplayed()))
        onView(instanceOf(RecyclerView::class.java)).check(hasItemCount(prvaIgra.userImpressions.size))
    }
    @Test
    fun detailsDugmeNakonItemClick(){
        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))
        onView(withId(R.id.home_button)).perform(click())
        onView(withId(R.id.details_button)).perform(click())
        onView(withText(prvaIgra.description)).check(matches(isCompletelyDisplayed()))
    }
}*/