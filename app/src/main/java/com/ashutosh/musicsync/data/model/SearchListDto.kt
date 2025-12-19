package com.ashutosh.musicsync.data.model

data class SearchListDto(
    val albums: AlbumsDto? = null,
    val artists: ArtistsDto? = null ,
    val episodes: EpisodesDto? = null,
    val playlists: PlaylistsDto? = null ,
    val shows: ShowsDto? = null ,
    val songs: SongsDto,
    val topquery: TopQueryDto? = null,
    val pids : AlbumMoreInfoDto? = null
)
