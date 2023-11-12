package com.saboon.myprograms.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.saboon.myprograms.adapter.MainFragmentProgramsRecyclerAdapter
import com.saboon.myprograms.adapter.ProgramFragmentRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectDetailsFragmentEventRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectDetailsFragmentRecyclerAdapter
import com.saboon.myprograms.adapter.SubjectsFragmentRecyclerAdapter
import com.saboon.myprograms.db.EventDAO
import com.saboon.myprograms.db.MyProgsDatabase
import com.saboon.myprograms.db.ProgramDAO
import com.saboon.myprograms.db.SubjectDAO
import com.saboon.myprograms.repo.EventRepository
import com.saboon.myprograms.repo.ProgramRepository
import com.saboon.myprograms.repo.SubjectRepository
import com.saboon.myprograms.repo.repositoryinterface.EventRepositoryInterface
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


    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `events` (`id` TEXT NOT NULL, `dateCreated` INTEGER NOT NULL, `dateModified` INTEGER NOT NULL, `ownerId` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `date` INTEGER NOT NULL, `timeStart` INTEGER NOT NULL, `timeEnd` INTEGER NOT NULL, `place` TEXT NOT NULL, `timeReminder` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE `events` ADD COLUMN `repeat` INTEGER NOT NULL")
        }
    }

    @Singleton
    @Provides
    fun provideMyProgsDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MyProgsDatabase::class.java,
        "MyProgramsDatabase"
    )
        .addMigrations(MIGRATION_2_3)
        .build()


    @Singleton
    @Provides
    fun provideProgramDao(db: MyProgsDatabase) = db.programDao()

    @Singleton
    @Provides
    fun provideSubjectDao(db: MyProgsDatabase) = db.subjectDao()

    @Singleton
    @Provides
    fun provideEventDao(db: MyProgsDatabase) = db.eventDao()


    @Singleton
    @Provides
    fun provideMainFragmentProgramRecyclerAdapter() : MainFragmentProgramsRecyclerAdapter = MainFragmentProgramsRecyclerAdapter()


    @Singleton
    @Provides
    fun provideProgramFragmentRecyclerAdapter(): ProgramFragmentRecyclerAdapter = ProgramFragmentRecyclerAdapter()

    @Singleton
    @Provides
    fun provideSubjectsFragmentRecyclerAdapter(): SubjectsFragmentRecyclerAdapter = SubjectsFragmentRecyclerAdapter()


    @Singleton
    @Provides
    fun provideSubjectDetailsFragmentRecyclerAdapter(): SubjectDetailsFragmentRecyclerAdapter = SubjectDetailsFragmentRecyclerAdapter()

    @Singleton
    @Provides
    fun provideSubjectDetailsFragmentEventRecyclerAdapter(): SubjectDetailsFragmentEventRecyclerAdapter = SubjectDetailsFragmentEventRecyclerAdapter()


    @Singleton
    @Provides
    fun provideProgramRepo(progDao: ProgramDAO) = ProgramRepository(progDao) as ProgramRepositoryInterface

    @Singleton
    @Provides
    fun provideSubjectRepo(subjectDAO: SubjectDAO) = SubjectRepository(subjectDAO) as SubjectRepositoryInterface

    @Singleton
    @Provides
    fun provideEventRepo(eventDAO: EventDAO) = EventRepository(eventDAO) as EventRepositoryInterface

}




