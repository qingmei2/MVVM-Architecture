package com.qingmei2.sample.db

import androidx.annotation.VisibleForTesting
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qingmei2.sample.entity.Repo

@Dao
interface UserReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<Repo>)

    @Query("SELECT * FROM user_repos ORDER BY indexInSortResponse ASC")
    fun queryRepos(): PagingSource<Int, Repo>

    @Query("DELETE FROM user_repos")
    suspend fun deleteAllRepos()

    @Query("SELECT MAX(indexInSortResponse) + 1 FROM user_repos")
    suspend fun getNextIndexInRepos(): Int?

    /**
     * 根据某个Id检索对应的 [Repo].
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query("SELECT * FROM user_repos WHERE id = :repoId")
    suspend fun getRepoById(repoId: String): Repo?

    /**
     * 获取所有的 [Repo].
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    @Query("SELECT * FROM user_repos")
    suspend fun getAllRepos(): List<Repo>
}
