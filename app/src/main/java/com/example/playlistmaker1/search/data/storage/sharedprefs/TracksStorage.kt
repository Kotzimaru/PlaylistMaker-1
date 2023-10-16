
import com.example.playlistmaker1.player.data.TrackDTO

interface TracksStorage {
    fun saveHistory(historyList: List<TrackDTO>)
    fun readHistory(): List<TrackDTO>
}