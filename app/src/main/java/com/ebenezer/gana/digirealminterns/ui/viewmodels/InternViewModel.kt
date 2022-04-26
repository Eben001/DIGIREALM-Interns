package com.ebenezer.gana.digirealminterns

import androidx.lifecycle.*
import com.ebenezer.gana.digirealminterns.model.Intern
import com.ebenezer.gana.digirealminterns.database.dao.InternsDao
import kotlinx.coroutines.launch

class InternViewModel(private val internsDao: InternsDao) : ViewModel() {


    val allInterns: LiveData<List<Intern>> =
        internsDao.getInterns().asLiveData()

    private fun insertIntern(intern: Intern){
        viewModelScope.launch {
            internsDao.insert(intern)
        }
    }
    private fun updateIntern(intern: Intern){
        viewModelScope.launch {
            internsDao.update(intern)
        }
    }

    fun retrieveIntern(id: Int): LiveData<Intern> {
        return internsDao.getIntern(id).asLiveData()

    }

    fun delete(intern: Intern){
        viewModelScope.launch {
            internsDao.delete(intern)
        }
    }

    fun updateIntern(internId:Int,
    internName: String, internSkills: String){
        val updatedIntern = getUpdatedInternEntry(
            internId, internName, internSkills
        )
        updateIntern(updatedIntern)

    }
    private fun getUpdatedInternEntry(
        internId: Int,
        internName: String,
        internSkills: String
    ): Intern {
        return Intern(id = internId,
        internName = internName,
        internAcquiredSkills = internSkills)
    }

    private fun getNewInternEntry(internName: String, internSkills: String): Intern {
        return Intern(internName = internName, internAcquiredSkills = internSkills)
    }

    fun isEntryValid(internName: String, internSkills: String): Boolean {
        if (internName.isBlank() || internSkills.isBlank()) {
            return false
        }
        return true
    }

    fun addNewIntern(internName: String, internSkills: String) {
        val newIntern = getNewInternEntry(internName, internSkills)
        insertIntern(newIntern)
    }

}


class InternViewModelFactory(private val internsDao: InternsDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InternViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InternViewModel(internsDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class ")

    }

}