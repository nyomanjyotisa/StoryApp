package id.jyotisa.storyapp

import id.jyotisa.storyapp.model.Story
import java.util.ArrayList

object DataDummy {
    var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVptUVFrek5Nd1ljZW9EMEMiLCJpYXQiOjE2NTA0MzcwMDN9.hDFbTQ0BA653FZz4CesDVl4qYrRB4JZ8CkLG_Y_XX34"

    fun generateDummyStoryResponse(): ArrayList<Story> {
        val items: ArrayList<Story> = arrayListOf()
        for (i in 0..100) {
            val quote = Story(
                i.toString(),
                "name + $i",
                "desc $i",
                "photo $i",
                "createdAt $i",
                i.toDouble(),
                i.toDouble(),
            )
            items.add(quote)
        }
        return items
    }
}