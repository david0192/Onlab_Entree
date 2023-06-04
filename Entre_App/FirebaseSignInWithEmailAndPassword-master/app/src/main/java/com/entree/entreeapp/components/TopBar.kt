package com.entree.entreeapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.entree.entreeapp.core.Constants.REVOKE_ACCESS
import com.entree.entreeapp.core.Constants.SIGN_OUT

@Composable
fun TopBar(
    title: String,
    signOut: () -> Unit,
    revokeAccess: () -> Unit,
) {
    var openMenu by remember { mutableStateOf(false) }

    TopAppBar (
        backgroundColor = Color.DarkGray,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title, color=Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            openMenu = !openMenu
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                            tint=Color.White
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = {
                    openMenu = !openMenu
                }
            ) {
                DropdownMenuItem(
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = SIGN_OUT
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        revokeAccess()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = REVOKE_ACCESS
                    )
                }
            }
        }
    )
}