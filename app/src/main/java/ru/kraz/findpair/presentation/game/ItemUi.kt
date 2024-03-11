package ru.kraz.findpair.presentation.game

import android.view.View
import ru.kraz.findpair.R

sealed class ItemUi(
    open val value: Int,
    open val visible: Int,
    protected open val pair: Int
) {

    abstract fun visible(list: MutableList<ItemUi>, index: Int, visible: Int = View.VISIBLE)

    open fun checkStatePair(list: MutableList<ItemUi>): Boolean =
        visible == list[pair].visible

    data class First(
        override val value: Int = R.drawable.baseline_beach_access_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) : ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = First(visible = visible, pair = pair)
        }
    }

    data class Second(
        override val value: Int = R.drawable.baseline_anchor_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) : ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Second(visible = visible, pair = pair)
        }
    }


    data class Third(
        override val value: Int = R.drawable.baseline_alarm_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) : ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Third(visible = visible, pair = pair)
        }
    }

    data class Fourth(
        override val value: Int = R.drawable.baseline_auto_stories_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) : ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Fourth(visible = visible, pair = pair)
        }
    }

    data class Fifth(
        override val value: Int = R.drawable.baseline_bedroom_baby_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Fifth(visible = visible, pair = pair)
        }
    }

    data class Sixth(
        override val value: Int = R.drawable.baseline_bug_report_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Sixth(visible = visible, pair = pair)
        }
    }

    data class Seventh(
        override val value: Int = R.drawable.baseline_broken_image_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Seventh(visible = visible, pair = pair)
        }
    }

    data class Eighth(
        override val value: Int = R.drawable.baseline_bookmark_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Eighth(visible = visible, pair = pair)
        }
    }

    data class Ninth(
        override val value: Int = R.drawable.baseline_bluetooth_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Ninth(visible = visible, pair = pair)
        }
    }

    data class Tenth(
        override val value: Int = R.drawable.baseline_commit_24,
        override val visible: Int = View.INVISIBLE,
        override val pair: Int
    ) :
        ItemUi(value, visible, pair) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Tenth(visible = visible, pair = pair)
        }
    }
}