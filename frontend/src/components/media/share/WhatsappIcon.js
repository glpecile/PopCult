import {IconButton} from "@mui/material";
import WhatsAppIcon from '@mui/icons-material/WhatsApp';

const WhatsappIcon = (props) => {
    return (
        <div className="flex flex-col justify-center items-center">
            <IconButton
                onClick={() => {
                    window.open("https://api.whatsapp.com/send/?phone&text=Hey!%20check%20this%20content%20out!%20" + document.title + "%20in%20" + props.url + "&app_absent=0")
                }}
                className="rounded-full shadow-md w-20 h-20 flex items-center justify-center transition duration-300 ease-in-out transform hover:-translate-1 hover:scale-105 active:scale-90 bg-green-400 hover:shadow-green-400/50">
                <WhatsAppIcon fontSize="large" className="text-white"/>
            </IconButton>
            <p className="text-center py-2">WhatsApp</p>
        </div>
    )
}

export default WhatsappIcon;