package com.segunfrancis.feature.home.model

data class ContentItem(
    val contentImage: String,
    val contentTitle: String,
) {
    companion object {
        fun getPagerItems(): List<ContentItem> {
            return listOf(
                ContentItem(
                    "https://images.unsplash.com/photo-1516585142943-4341daf22d5f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8d29tYW4lMjBjcnlpbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Digital Assault can put you into jail for 15 years?"
                ),

                ContentItem(
                    "https://st3.depositphotos.com/10654668/13721/i/600/depositphotos_137216558-stock-photo-young-woman-crying.jpg",
                    "Digital Assault can put you into jail for 15 years?"
                ),

                ContentItem(
                    "https://images.unsplash.com/photo-1521075486433-bf4052bb37bc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8d29tYW4lMjBjcnlpbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Digital Assault can put you into jail for 15 years?"
                ),

                ContentItem(
                    "https://images.unsplash.com/photo-1475609471617-0ef53b59cff5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8d29tYW4lMjBjcnlpbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Digital Assault can put you into jail for 15 years?"
                )
            )
        }
    }
}
