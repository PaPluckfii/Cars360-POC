package com.sumeet.cars360.ui.customer.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.FragmentCustomerHelpBinding
import com.sumeet.cars360.ui.customer.CustomerViewModel
import com.sumeet.cars360.ui.customer.util.FAQRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerHelpFragment : Fragment() {

    private lateinit var binding: FragmentCustomerHelpBinding
    private val viewModel: CustomerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerHelpBinding.inflate(inflater,container,false)
        return binding.root
    }

    private val lisOfFAQs = listOf(
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
        "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                "on every service."),
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
            "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                    " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                    " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                    " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                    "on every service."),
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
            "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                    " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                    " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                    " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                    "on every service."),
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
            "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                    " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                    " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                    " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                    "on every service."),
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
            "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                    " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                    " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                    " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                    "on every service."),
        Pair("Why do I take regular servicing such as oil change from Cars 360?",
            "Cars 360, is an only setup in entire Chhattisgarh, with all modern and latest technology," +
                    " compared to other dealerships in state. By taking services from us, 3 basic advantages are, Cost economical," +
                    " Time saving through avoiding long ques and transparency in job, as at our place, parts are replaced only when" +
                    " they are actually required to, instead of following the service protocol of manufacturer to change everything " +
                    "on every service."),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.faqRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FAQRecyclerAdapter(lisOfFAQs)
        }

        handleListeners()

    }

    private fun handleListeners() {
        binding.apply {

            llCallUs.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919584390909")))
                }catch (e: Exception) {
                    Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            llEnquiry.setOnClickListener {
                Toast.makeText(context, "Enquiry is disabled for now", Toast.LENGTH_SHORT)
                    .show()
            }

            llEmailUs.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_SENDTO,
                            Uri.fromParts(
                                "mailto","admin@cars360.in", null
                            )
                        ).putExtra(Intent.EXTRA_SUBJECT, "Enquiry From App")
                            .putExtra(Intent.EXTRA_TEXT, "Hi, ")
                    )
                }catch (e: Exception) {
                    Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            llLocateUs.setOnClickListener {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "https://www.google.com/maps/place/" +
                                        "Cars+360%C2%B0/@21.235233,81.589693,15z/data=" +
                                        "!4m5!3m4!1s0x0:0x7dace6e394d698e3!8m2!3d21.2352333!4d81." +
                                        "5896934?hl=en-US"
                            )
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(context, "Something went wrong - ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}