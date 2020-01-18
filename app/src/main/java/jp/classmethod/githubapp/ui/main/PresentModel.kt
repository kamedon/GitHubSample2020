package jp.classmethod.githubapp.ui.main

class UserPresentModel(val id: String, val reposUrl: String) {
    companion object {
        val NONE = UserPresentModel("", "")
    }
}

class FeedPresentModel(val timelineUrl: String) {
    companion object {
        val NONE = FeedPresentModel("")
    }

}
