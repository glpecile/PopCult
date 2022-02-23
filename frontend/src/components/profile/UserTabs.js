import {useState} from "react";
import {useTranslation} from "react-i18next";
import {Tab, Tabs} from "@mui/material";

function TabPanel(props) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`tabpanel-${index}`}
            aria-labelledby={`tab-${index}`}
            {...other}
        >
            {value === index && (
                <div className="p-3">
                    {children}
                </div>
            )}
        </div>
    );
}

function a11yProps(index) {
    return {
        id: `tab-${index}`,
        'aria-controls': `tabpanel-${index}`,
    };
}

const UserTabs = (props) => {
    const {t} = useTranslation();
    const [value, setValue] = useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <>
            <Tabs value={value}
                  onChange={handleChange}
                  textColor="secondary"
                  indicatorColor="secondary"
                  aria-label="tabs">
                <Tab label={t('profile_tabs_main', {username: props.username})} {...a11yProps(0)}/>
                <Tab label={t('profile_tabs_favMedia')} {...a11yProps(1)}/>
                <Tab label={t('profile_tabs_favLists')} {...a11yProps(2)}/>
                <Tab label={t('profile_tabs_watchedMedia')} {...a11yProps(3)}/>
                <Tab label={t('profile_tabs_watchedMedia')} {...a11yProps(4)}/>
            </Tabs>

            <TabPanel value={value} index={0}>
                Content 1
            </TabPanel>

            <TabPanel value={value} index={1}>
                Content 2
            </TabPanel>

            <TabPanel value={value} index={2}>
                Content 3
            </TabPanel>

            <TabPanel value={value} index={3}>
                Content 4
            </TabPanel>

            <TabPanel value={value} index={4}>
                Content 5
            </TabPanel>
        </>
    );
}
export default UserTabs;