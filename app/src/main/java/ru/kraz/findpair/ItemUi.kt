package ru.kraz.findpair

import android.view.View

sealed class ItemUi(
    open val value: Int,
    open val visible: Int,
) {

    abstract fun visible(list: MutableList<ItemUi>, index: Int, visible: Int = View.VISIBLE)

    data class First(
        override val value: Int = R.drawable.baseline_beach_access_24,
        override val visible: Int = View.INVISIBLE,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = First(visible = visible)
        }
    }

    data class Second(
        override val value: Int = R.drawable.baseline_anchor_24,
        override val visible: Int = View.INVISIBLE,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Second(visible = visible)
        }
    }


    data class Third(
        override val value: Int = R.drawable.baseline_alarm_24,
        override val visible: Int = View.INVISIBLE,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Third(visible = visible)
        }
    }

    data class Fourth(
        override val value: Int = R.drawable.baseline_auto_stories_24,
        override val visible: Int = View.INVISIBLE,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Fourth(visible = visible)
        }
    }

    data class Fifth(
        override val value: Int = R.drawable.baseline_bedroom_baby_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Fifth(visible = visible)
        }
    }

    data class Sixth(
        override val value: Int = R.drawable.baseline_bug_report_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Sixth(visible = visible)
        }
    }

    data class Seventh(
        override val value: Int = R.drawable.baseline_broken_image_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Seventh(visible = visible)
        }
    }

    data class Eighth(
        override val value: Int = R.drawable.baseline_bookmark_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Eighth(visible = visible)
        }
    }

    data class Ninth(
        override val value: Int = R.drawable.baseline_bluetooth_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Ninth(visible = visible)
        }
    }

    data class Tenth(
        override val value: Int = R.drawable.baseline_commit_24,
        override val visible: Int = View.INVISIBLE,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Int) {
            list[index] = Tenth(visible = visible)
        }
    }
}