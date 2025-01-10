package com.example.myapplication

import android.view.MenuItem

interface NameChangeListener {
    fun onNameChanged(newName: String)
    fun onNavigationItemSelected(item: MenuItem): Boolean
}