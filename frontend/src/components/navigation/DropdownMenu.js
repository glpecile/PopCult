import {NavLink} from "react-router-dom";
import {useTranslation} from "react-i18next";
import {useContext, useState} from "react";
import AuthContext from "../../store/AuthContext";
import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import ArrowDropUpOutlinedIcon from '@mui/icons-material/ArrowDropUpOutlined';
import {Menu} from "@mui/material";
import MenuItem from '@mui/material/MenuItem';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import ListOutlinedIcon from '@mui/icons-material/ListOutlined';
import NotificationsNoneOutlinedIcon from '@mui/icons-material/NotificationsNoneOutlined';
import AdminPanelSettingsOutlinedIcon from '@mui/icons-material/AdminPanelSettingsOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';

const DropdownMenu = () => {
    const authContext = useContext(AuthContext);
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const [t] = useTranslation();
    return (
        <>
            <div className="flex justify-end nav-item text-lg py-2 pl-2"
                 onClick={handleClick}
                 aria-controls={open ? 'account-menu' : undefined}
                 aria-haspopup="true"
                 aria-expanded={open ? 'true' : undefined}
            >
                <span className="transition duration-200 ease-in-out transform hover:-translate-1 active:scale-90 hover:scale-110">
                    {authContext.username}
                </span>
                {anchorEl ? <ArrowDropUpOutlinedIcon/> : <ArrowDropDownOutlinedIcon/>}
            </div>

            <Menu
                className="items-center"
                anchorEl={anchorEl}
                id="account-menu"
                open={open}
                onClose={handleClose}
                onClick={handleClose}
                PaperProps={{
                    elevation: 0,
                    sx: {
                        overflow: 'visible',
                        filter: 'drop-shadow(0px 2px 4px rgba(0,0,0,0.16))',
                        mt: 1.5,
                        '& .MuiAvatar-root': {
                            width: 32,
                            height: 32,
                            ml: -0.5,
                            mr: 1,
                        },
                        '&:before': {
                            content: '""',
                            display: 'block',
                            position: 'absolute',
                            top: 0,
                            right: 14,
                            width: 10,
                            height: 10,
                            bgcolor: 'background.paper',
                            transform: 'translateY(-50%) rotate(45deg)',
                            zIndex: 0,
                        },
                    },
                }}
                transformOrigin={{horizontal: 'right', vertical: 'top'}}
                anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
            >
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to='/'>
                        <HomeOutlinedIcon/> {t('nav_home')}
                    </NavLink>
                </MenuItem>
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to={'/user/' + authContext.username}>
                        <AccountCircleOutlinedIcon/> {t('nav_profile')}
                    </NavLink>
                </MenuItem>
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to={'/user/a'}>
                        <ListOutlinedIcon/> {t('nav_my_lists')}
                    </NavLink>
                </MenuItem>
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to={'/user/b'}>
                        <NotificationsNoneOutlinedIcon/> {t('nav_notifications')}
                    </NavLink>
                </MenuItem>
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to='/admin'>
                        <AdminPanelSettingsOutlinedIcon/> {t('nav_admin')}
                    </NavLink>
                </MenuItem>
                <MenuItem>
                    <NavLink className="stretched-link text-slate-800" to='/login' onClick={authContext.onLogout}>
                        <LogoutOutlinedIcon/> {t('nav_sign_out')}
                    </NavLink>
                </MenuItem>
            </Menu>
        </>
    )
        ;
}
export default DropdownMenu;