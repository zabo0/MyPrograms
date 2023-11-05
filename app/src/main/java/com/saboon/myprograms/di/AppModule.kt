package com.saboon.myprograms.di

import android.content.Context
import androidx.room.Room
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import com.saboon.myprograms.db.MyProgsDatabase
import com.saboon.myprograms.db.ProgramDAO
import com.saboon.myprograms.db.SubjectDAO
import com.saboon.myprograms.repo.ProgramRepository
import com.saboon.myprograms.repo.SubjectRepository
import com.saboon.myprograms.repo.repositoryinterface.ProgramRepositoryInterface
import com.saboon.myprograms.repo.repositoryinterface.SubjectRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyProgsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MyProgsDatabase::class.java,
        "MyProgramsDatabase"
    ).build()


    @Singleton
    @Provides
    fun provideProgramDao(db: MyProgsDatabase) = db.programDao()

    @Singleton
    @Provides
    fun provideSubjectDao(db: MyProgsDatabase) = db.subjectDao()


    @Singleton
    @Provides
    fun provideMainFragmentProgramRecyclerAdapter() : MainFragmentProgramsRecyclerAdapter = MainFragmentProgramsRecyclerAdapter()


    @Singleton
    @Provides
    fun provideProgramFragmentRecyclerAdapter(): ProgramFragmentRecyclerAdapter = ProgramFragmentRecyclerAdapter()



    @Singleton
    @Provides
    fun provideProgramRepo(progDao: ProgramDAO) = ProgramRepository(progDao) as ProgramRepositoryInterface

    @Singleton
    @Provides
    fun provideSubjectRepo(subjectDAO: SubjectDAO) = SubjectRepository(subjectDAO) as SubjectRepositoryInterface

}




